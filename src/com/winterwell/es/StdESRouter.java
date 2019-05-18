package com.winterwell.es;


public class StdESRouter implements IESRouter {

	@Override
	public ESPath getPath(CharSequence dataspaceIsIgnored, Class type, CharSequence id, Object status) {
		// map personlite and person to the same DB
//		if (type==PersonLite.class) type = Person.class;
		String stype = type.getSimpleName().toLowerCase();
		// type==NGO.class? "charity" :
		// HACK to implement KStatus handling without a class dependency :(
		String index = stype;
		String ks = status==null? "PUBLISHED" : status.toString().toUpperCase();
		switch(ks) {
		case "PUBLISHED":
			break;
		case "DRAFT": case "PENDING": case "REQUEST_PUBLISH": case "MODIFIED":
			index += ".draft";
			break;
		case "TRASH":
			index += ".trash";
			break;
		case "ALL_BAR_TRASH":
			String i1 = index;
			String i2 = index+".draft";
			ESPath esp = new ESPath(new String[] {i1, i2}, stype, id==null? null : id.toString());
			return esp;
		default:
			throw new IllegalArgumentException(type+" "+status);
		}
		return new ESPath(index, stype, id);
	}


}
