package com.blit.lp.jf.config;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.util.JdbcConstants;
import com.blit.blit_jlzz.jf.SqlDecodeDirective;
import com.blit.blit_jlzz.plugin.InitViewTablePlugin;
import com.blit.lp.core.context.Global;
import com.blit.lp.jf.handler.ContextHandler;
import com.blit.lp.jf.interceptor.AuthInterceptor;
import com.blit.lp.jf.interceptor.SysExceptionInterceptor;
import com.blit.lp.jf.interceptor.flow.FlowInterceptor;
import com.blit.lp.jf.plugin.Cron4jTaskPlugin;
import com.blit.lp.tools.ClassScanKit;
import com.jfinal.aop.Duang;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.Dialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import java.io.File;

public class LPConfig extends JFinalConfig {

    /**
     * 启动Jetty服务
     *
     * @param args
     */
    public static void main(String[] args) {
        JFinal.start("WebRoot", 8081, "/", 5);
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();
        Ret.setToOldWorkMode();
        
        FreeMarkerRender.setTemplateLoadingPath("/view");

        FreeMarkerRender.setProperty("template_update_delay", "0");
        FreeMarkerRender.setProperty("classic_compatible", "true");
        FreeMarkerRender.setProperty("whitespace_stripping", "true");
        FreeMarkerRender.setProperty("date_format", "yyyy-MM-dd");
        FreeMarkerRender.setProperty("time_format", "HH:mm:ss");
        FreeMarkerRender.setProperty("datetime_format", "yyyy-MM-dd HH:mm");
        FreeMarkerRender.setProperty("default_encoding", "UTF-8");
    }

    @Override
    public void configConstant(Constants me) {
        me.setDevMode(Global.isDebug());

        me.setViewType(ViewType.JFINAL_TEMPLATE);
        me.setError500View("/sys/err.html");
        me.setBaseUploadPath(PropKit.get("uploaddir"));
        me.setMaxPostSize(1024 * 1024 * PropKit.getInt("maxuploadsize", 10));
    }

    @Override
    public void configEngine(Engine me) {
        me.setBaseTemplatePath(PathKit.getWebRootPath() + "/view");
        me.addSharedFunction("/sys/common/templatefunction.html");
        me.setDatePattern("yyyy-MM-dd HH:mm");
    }

    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextHandler());

        DruidStatViewHandler dvh = new DruidStatViewHandler("/druid", (HttpServletRequest request) -> true);
        me.add(dvh);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new SysExceptionInterceptor());
        me.add(new AuthInterceptor());
        me.add(new FlowInterceptor());
    }

    @Override
    public void configPlugin(Plugins me) {
        loadDb(me);

        me.add(new Cron4jTaskPlugin());
        me.add(new InitViewTablePlugin());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void configRoute(Routes me) {
        String packages = PropKit.get("package.controllers");
        for (String p : packages.split(",")) {
            Set<Class<?>> set = ClassScanKit.getClasses(p);
            Iterator<Class<?>> it = set.iterator();
            while (it.hasNext()) {
                Class<?> cls = it.next();
                if (Controller.class.isAssignableFrom(cls)) {
                    LPController ctrlAnnotation = cls.getAnnotation(LPController.class);
                    if (ctrlAnnotation != null) {
                        me.add(ctrlAnnotation.controllerkey(), (Class<? extends Controller>) cls);
                    }

                }
            }
        }

    }

    private void loadDb(Plugins me) {
        //数据源加载
        String ds = PropKit.get("db.datasources");
        for (String dbname : ds.split(",")) {
            String dbKey = dbname + ".";
            String url = PropKit.get(dbKey + "url");
            String username = PropKit.get(dbKey + "username");
            String password = PropKit.get(dbKey + "password");
            String driver_class = PropKit.get(dbKey + "driver_class");
            int initialSize = PropKit.getInt(dbKey + "initialSize");
            int minIdle = PropKit.getInt(dbKey + "minIdle");
            int maxActive = PropKit.getInt(dbKey + "maxActive");
            boolean showSql = "true".equalsIgnoreCase(PropKit.get(dbKey + "showSql"));
            boolean toLowerCase = PropKit.getBoolean(dbKey + "record_to_json_lowercase", true);

            DruidPlugin dp = new DruidPlugin(url, username, password);
            dp.set(initialSize, minIdle, maxActive);
            dp.setDriverClass(driver_class);

            String dbType = null;
            Dialect dialect = null;
            if (url.toLowerCase().contains(JdbcConstants.ORACLE)) {
                dbType = JdbcConstants.ORACLE;
                dialect = new OracleDialect();
                dp.setValidationQuery("select 1 from dual");
            } else if (url.toLowerCase().contains(JdbcConstants.SQL_SERVER)) {
                dbType = JdbcConstants.SQL_SERVER;
                dialect = new AnsiSqlDialect();
            } else if (url.toLowerCase().contains(JdbcConstants.MYSQL)) {
                dbType = JdbcConstants.MYSQL;
                dialect = new MysqlDialect();
            } else if (url.toLowerCase().contains(JdbcConstants.POSTGRESQL)) {
                dbType = JdbcConstants.POSTGRESQL;
                dialect = new PostgreSqlDialect();
            } else {
                throw new RuntimeException("不支持的数据库！");
            }

            dp.addFilter(new StatFilter());
            me.add(dp);

            ActiveRecordPlugin arp = new ActiveRecordPlugin(dbname, dp);
            arp.setShowSql(showSql);
            arp.setDialect(dialect);
            arp.setContainerFactory(new CaseInsensitiveContainerFactory(toLowerCase));

            //添加SQL模板支持
            arp.setBaseSqlTemplatePath(PathKit.getRootClassPath() + File.separator + "sql" + File.separator + "templates");
            arp.addSqlTemplate("all.sql");

            //添加自定义SQL模板
            arp.getEngine()
                    .addDirective("decode", SqlDecodeDirective.class)
                    .addSharedObject("strKit", new com.jfinal.kit.StrKit());

            me.add(arp);
        }
    }

}
