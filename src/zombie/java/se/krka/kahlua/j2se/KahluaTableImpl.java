package se.krka.kahlua.j2se;

import java.util.Map;

import se.krka.kahlua.vm.KahluaTable;
import zombie.ZomboidClass;

@ZomboidClass
@SuppressWarnings("ALL")
public class KahluaTableImpl implements KahluaTable {
	public Map<Object, Object> delegate;
}
