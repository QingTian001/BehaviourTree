package config.behaviour.expnumber;

// GetCurrentTimeMills
public final class GetCurrentTimeMills implements config.behaviour.expnumber.ExpNumber {
    @Override
    public config.behaviour.Expnumberenum type() {
        return config.behaviour.Expnumberenum.GETCURRENTTIMEMILLS;
    }


    public GetCurrentTimeMills() {
    }

    public static GetCurrentTimeMills _create(configgen.genjava.ConfigInput input) {
        GetCurrentTimeMills self = new GetCurrentTimeMills();
        return self;
    }

    @Override
    public int hashCode() {
        return GetCurrentTimeMills.class.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof GetCurrentTimeMills;
    }

    @Override
    public String toString() {
        return "GetCurrentTimeMills";
    }

}
