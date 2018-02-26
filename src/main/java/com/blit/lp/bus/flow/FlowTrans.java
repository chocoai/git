package com.blit.lp.bus.flow;

import java.util.List;
import java.util.regex.Pattern;

import com.blit.lp.bus.flow.c.CompareTypeEnum;
import com.blit.lp.bus.flow.c.ConditionTypeEnum;
import com.blit.lp.core.exception.FlowException;
import com.blit.lp.tools.LPLogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

/**
 * 流程迁移线判断
 * @author dkomj
 *
 */
public class FlowTrans {
	private FlowInstance flowInstance = null;
	private Record flowTransRow = null;
	public FlowTrans(FlowInstance fi, Record row){
		flowInstance = fi;
		flowTransRow = row;
	}
	
	public boolean isEnable(){
		String conditionid = flowTransRow.getStr("conditionid");
		if(StrKit.notBlank(conditionid) && !conditionid.equals("-1")){
			return this.runCondition(conditionid);
		}
		
		return true;
	}
	
	private boolean runCondition(String conditionid){
		{
			Record conditionRow = getCondition(conditionid);
			if(conditionRow == null)
				throw new FlowException("找不到流程条件！id：" + conditionid);
			
			boolean isEnabled = false;
			try
			{
				int conditiontype = Integer.parseInt(conditionRow.get("conditiontype").toString());
				int comparetype = Integer.parseInt(conditionRow.get("comparetype").toString());
				String left = conditionRow.getStr("leftval");
				String right = conditionRow.getStr("rightval");

				Object leftValue = null;
				Object rightValue = null;
				switch (ConditionTypeEnum.valueOf(conditiontype))
				{
				case PARAM_PARAM:
					leftValue = flowInstance.getParam(left);
					rightValue = flowInstance.getParam(right);
					break;
				case PARAM_CONST:
					leftValue = flowInstance.getParam(left);
					rightValue = right;
					
					break;
				case PARAM_CON:
					leftValue = flowInstance.getParam(left);
					rightValue = this.runCondition(right);
					break;
				case CON_CON:
					leftValue = this.runCondition(left);
					rightValue = this.runCondition(right);
					break;
				case CON_CONST:
					leftValue = this.runCondition(left);
					rightValue = right;
					break;
				case CONST_CONST:
					leftValue = left;
					rightValue = right;
					break;
				}
				
				if("".equals(leftValue))
					leftValue = null;
				if("".equals(rightValue))
					rightValue = null;
				switch(CompareTypeEnum.valueOf(comparetype))
				{//String,int,bool
					case LESS:
						if(leftValue==null)
						{
							if(rightValue ==null)
							{
								isEnabled = false;
							}
							else
							{
								isEnabled = true;
							}
						}
						else
						{
							if(rightValue ==null)
							{
								isEnabled = false;
							}
							else
							{
								isEnabled = compareTo(leftValue,rightValue) < 0;
							}
						}
					break;
					case LESSEQUAL:
						if(leftValue==null)
						{
							isEnabled = true;
						}
						else
						{
							if(rightValue ==null)
							{
								isEnabled = true;
							}
							else
							{
								isEnabled = compareTo(leftValue,rightValue) <= 0;
							}
						}
					break;
					case BIG:
						if(leftValue==null)
						{
							isEnabled = false;
						}
						else
						{
							if(rightValue ==null)
							{
								isEnabled = false;
							}
							else
							{
								isEnabled = compareTo(leftValue,rightValue) > 0;
							}
						}
					break;
					case BIGEQUAL:
						if(leftValue==null)
						{
							if(rightValue ==null)
							{
								isEnabled = true;
							}
							else
							{
								isEnabled = false;
							}
						}
						else
						{
							if(rightValue ==null)
							{
								isEnabled = true;
							}
							else
							{
								isEnabled = compareTo(leftValue,rightValue) >= 0;
							}
						}
					break;
					case EQUAL:
						if(leftValue==null)
						{
							if(rightValue ==null)
							{
								isEnabled = true;
							}
							else
							{
								isEnabled = false;
							}
						}
						else
						{
							if(rightValue ==null)
							{
								isEnabled = false;
							}
							else
							{
								isEnabled = compareTo(leftValue,rightValue) == 0;
							}
						}
					break;
					case NOTEQUAL:
						if(leftValue==null)
						{
							if(rightValue ==null)
							{
								isEnabled = false;
							}
							else
							{
								isEnabled = true;
							}
						}
						else
						{
							if(rightValue ==null)
							{
								isEnabled = true;
							}
							else
							{
								isEnabled = compareTo(leftValue,rightValue) != 0;
							}
						}
					break;
					case AND:
						if(leftValue==null || rightValue ==null)
						{
							isEnabled = false;
						}
						else
						{
							Boolean bL = convertBoolean(leftValue);
							Boolean bR = convertBoolean(rightValue);
							isEnabled = bL.booleanValue() && bR.booleanValue();
						}
					break;
					case OR:
						if(leftValue==null && rightValue ==null)
						{
							isEnabled = false;
						}
						else
						{
							Boolean bL = convertBoolean(leftValue);
							Boolean bR = convertBoolean(rightValue);
							isEnabled = bL.booleanValue() || bR.booleanValue();
						}
					break;
					case CONTAIN:
						if(leftValue==null)
						{
							isEnabled = false;
						}
						else
						{
							if(rightValue !=null)
							{
								if(leftValue instanceof String)
								{
									isEnabled = ((String)leftValue).indexOf(rightValue.toString())>=0;
								}
								else
								{
									throw new Exception("只有当操作数左值为String类型时才可以使用“包含”");
								}
							}
							else
							{
								isEnabled = true;
							}
						}
					break;
					case NOTCONTAIN:
						if(leftValue==null)
						{
							isEnabled = true;
						}
						else
						{
							if(rightValue ==null)
							{
								isEnabled = false;
							}
							else
							{
								if(leftValue instanceof String)
								{
									isEnabled = ((String)leftValue).indexOf(rightValue.toString())<0;
								}
								else
								{
									throw new Exception("只有当操作数左值为String类型时才可以使用“不包含”");
								}
							}
						}
					break;
					case LEFTCONTAIN:
						if(leftValue==null)
						{
							isEnabled = true;
						}
						else
						{
							if(rightValue ==null)
							{
								isEnabled = false;
							}
							else
							{
								if(leftValue instanceof String)
								{
									isEnabled = ((String)leftValue).startsWith(rightValue.toString());
								}
								else
								{
									throw new Exception("只有当操作数左值为String类型时才可以使用“左包含”");
								}
							}
						}
					break;
					case RIGHTCONTAIN:
						if(leftValue==null)
						{
							isEnabled = true;
						}
						else
						{
							if(rightValue ==null)
							{
								isEnabled = false;
							}
							else
							{
								if(leftValue instanceof String)
								{
									String l = (String)leftValue;
									String r = rightValue.toString();
									isEnabled = l.endsWith(r);
								}
								else
								{
									throw new Exception("只有当操作数左值为String类型时才可以使用“左包含”");
								}
							}
						}
					break;
				}
			}
			catch(Exception exp)
			{
				LPLogKit.error("计算流程条件出错：", exp);
				isEnabled = false;
			}
			return isEnabled;
		}
	}
	
	private Record getCondition(String conditionid){
		List<Record> list = flowInstance.getFlow().getConditionList();
		for (Record record : list) {
			if(conditionid.equals(record.getStr("id"))){
				return record;
			}
		}
		
		return null;
	}
	
	private static int compareTo(Object left,Object right){
		String vLeft = left.toString();
		String vRight = right.toString();
		Pattern pattern = Pattern.compile("^[.\\d]*$");    
	    if(pattern.matcher(vLeft).matches()
	    		&& pattern.matcher(vRight).matches())
	    {
	    	return ((Double)Double.parseDouble(vLeft)).compareTo((Double)Double.parseDouble(vRight));
	    }
	    else{
	    	return vLeft.compareTo(vRight);
	    }
	}
	
	private static Boolean convertBoolean(Object val){
		Boolean bl = Boolean.FALSE;
		if(val instanceof String)
		{
			bl = new Boolean(((String)val).equalsIgnoreCase("true"));
		}
		else if(val instanceof Integer)
		{
			bl = new Boolean(((Integer)val).intValue()==1);
		}
		else if(val instanceof Double)
		{
			val = new Boolean(((Double)val).intValue()==1);
		}
		else if(!(val instanceof Boolean))
		{
			val = Boolean.TRUE;
		}
		
		return bl;
	}
}
