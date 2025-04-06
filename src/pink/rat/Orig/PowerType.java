package pink.rat.Orig;

import java.util.function.Supplier;

import pink.rat.Powers.AustraliaPower;
import pink.rat.Powers.DummyPower;
import pink.rat.Powers.EggPower;
import pink.rat.Powers.LactosePower;

public enum PowerType {
	
	DUMMY(DummyPower::new),
	AUSSIE(AustraliaPower::new),
	LACTOSE(LactosePower::new),
	EGG(EggPower::new)
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
