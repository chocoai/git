package com.blit.lp.jf.interceptor;

import java.lang.reflect.InvocationTargetException;

import com.blit.lp.bus.auditlog.LogStatusEnum;
import com.blit.lp.bus.auditlog.LogTypeEnum;
import com.blit.lp.core.context.User;
import com.blit.lp.core.exception.LoginException;
import com.blit.lp.core.exception.NoAccessException;
import com.blit.lp.core.exception.SysException;
import com.blit.lp.tools.AuditLogKit;
import com.blit.lp.tools.LPLogKit;
import com.blit.lp.tools.ReflectException;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.activerecord.ActiveRecordException;

/**
 * 系统异常
 *
 * @author dkomj
 *
 */
public class SysExceptionInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        try {
            inv.invoke();
        } catch (LoginException e) {
            LPLogKit.error("登录错误：" + e.getMessage());
            AuditLogKit.log(LogTypeEnum.SYSLOGIN, LogStatusEnum.FAIL, e.getMessage());
            e.render(inv.getController());
        } catch (NoAccessException e) {
            String info = String.format("%s；访问帐号：%s；访问地址：%s",
                    e.getMessage(), User.getCurrUser().get("usercode"), inv.getActionKey());
            LPLogKit.warn(info);
            AuditLogKit.log(LogTypeEnum.SYSSECURITY, LogStatusEnum.FAIL, info);
            inv.getController().setAttr("errmsg", info);
            inv.getController().renderTemplate("/sys/err.html");
        } catch (SysException e) {
            LPLogKit.error("系统发生异常", e);
            e.render(inv.getController());
        } catch (ReflectException e) {
            if (e.getCause() instanceof InvocationTargetException) {
                InvocationTargetException e2 = (InvocationTargetException) e.getCause();
                SysException sysE = (SysException) e2.getTargetException();
                if (sysE != null) {
                    LPLogKit.error("系统发生异常", e);
                    sysE.render(inv.getController());
                }
            }

            LPLogKit.error("系统发生未知错误", e);
            new SysException("系统发生未知错误").render(inv.getController());
//			inv.getController().setAttr("errmsg", "系统发生未知错误!");
//			inv.getController().renderTemplate("/sys/err.html");
        } catch (ActiveRecordException e) {
            LPLogKit.error("系统执行SQL出错", e);
            new SysException("系统执行SQL出错").render(inv.getController());
        } catch (Exception e) {
            LPLogKit.error("系统发生未知错误", e);
            new SysException("系统发生未知错误").render(inv.getController());
//			inv.getController().setAttr("errmsg", "系统发生未知错误!");
//			inv.getController().renderTemplate("/sys/err.html");
        }
    }

}
