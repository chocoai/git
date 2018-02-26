package com.blit.lp.bus.record;

import java.util.HashMap;
import java.util.Map;

public class TypeMapping {
	
	@SuppressWarnings("serial")
	protected Map<String, Class<?>> map = new HashMap<String, Class<?>>() {{
		
		put("java.sql.Date", java.sql.Date.class);
		
		put("java.sql.Time", java.sql.Date.class);
		
		put("java.sql.Timestamp", java.sql.Date.class);
		
		put("[B", byte[].class);
		
		put("java.lang.String", java.lang.String.class);

		put("java.lang.Integer", java.lang.Integer.class);
		
		put("java.lang.Long", java.lang.Long.class);
		
		put("java.lang.Double", java.lang.Double.class);
		
		put("java.lang.Float", java.lang.Float.class);
		
		put("java.lang.Boolean", java.lang.Boolean.class);
		
		put("java.math.BigDecimal", java.math.BigDecimal.class);
		
		put("java.math.BigInteger", java.math.BigInteger.class);
	}};
	
	public Class<?> getType(String typeString) {
		return map.get(typeString);
	}
}