package pink.rat.Powers;

import java.util.function.Supplier;

public enum PowerType {
	
	DUMMY(DummyPower::new),
	AUSSIE(AustraliaPower::new),
	LACTOSE(LactosePower::new)
	;
	
	private final Supplier<Power> constructor;
	
	PowerType(Supplier<Power> constructor) {
		this.constructor = constructor;
	}
	
	public Power get() {
		return constructor.get();
	}
	
	public static PowerType fromString(String i) {
        try {
            return PowerType.valueOf(i.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
	}

}
