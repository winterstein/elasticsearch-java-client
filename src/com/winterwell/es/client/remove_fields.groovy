for(f in removals) {
	String[] fbits = f.split("\\.");
	Object target = ctx._source;
	if (resultField!=null) ctx._source[resultField] = true;
	for(bi=0; bi<fbits.length; bi++) {
		String fbi = fbits[bi];
		if (bi==fbits.length-1) {
			if (target!=null && target.containsKey(fbi)) target.remove(fbi);
			else if (resultField!=null) ctx._source[resultField] = false;
		} else {			
			if (target!=null) target = target[fbi];							
		}
	}
}
		