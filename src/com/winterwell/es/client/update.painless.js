// Basic update: merges maps and lists. Does NOT recurse
Map e=ctx._source;
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
			// merge in Map key-values
			old.putAll(v);
		} else if (v instanceof List) {
			// Handle the case where old isnt a List (see lg bugs May 2019)
			if ( ! old instanceof Collection) {
				def v2 = new ArrayList();
				v2.add(old); v2.addAll(v);
				e.put(k, v2);
			} else {
				// merge in new List values
				for(int i=0; i<v.size(); i++) {
					def x=v.get(i);
					if ( ! old.contains(x)) old.add(x);				
				}
			}
		} else {
			// just overwrite
			e.put(k, v);
		}
	}
}