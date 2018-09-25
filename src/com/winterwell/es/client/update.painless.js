def p0 = params.p0;
for(def kv : p0.entrySet()) {
	def k = kv.getKey();
	def v = kv.getValue();
	def old = e.get(k);
	if (old==null) {
		e.put(k, v);
	} else {
// assume: old-new type does not change
		if (v instanceof Map) {
			old.putAll(v);
		} else if (v instanceof List) {
			for(int i=0; i<v.size(); i++) {
				def x=v.get(i); 
				if ( ! old.contains(x)) old.add(x);
			}
		} else {
			e.put(k, v);
		}
	}
}