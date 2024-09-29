package liuuu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SerializationTypeEnum {

    KRYO((byte)0x01, "kryo"),
    PROTOSTUFF((byte)0x02, "protostuff"),
    HESSIAN((byte)0x03, "hessian");

    private final byte code;
    private final String name;

    public static String getName(byte code) {
        for (SerializationTypeEnum s : SerializationTypeEnum.values()) {
            if (s.code == code) {
                return s.name;
            }
        }
        return null;
    }
}
