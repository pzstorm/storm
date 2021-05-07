package se.krka.kahlua.j2se;

import se.krka.kahlua.vm.KahluaTable;
import zombie.ZomboidClass;

import java.util.Map;

@ZomboidClass
public class KahluaTableImpl implements KahluaTable {

	public Map<Object, Object> delegate;
}
