package liuuu.enums;


import liuuu.registry.ServiceDiscovery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SerializationTypeEnum {

    KYRO((byte)0x01, "kyro"),
    PROTOSTUFF((byte)0x02, "protostuff"),
    HESSIAN((byte)0x03, "hessian");

    private final byte code;
    private final String name;

    private static String getName(byte code) {
        for (SerializationTypeEnum s : SerializationTypeEnum.values()) {
            if (s.code == code) {
                return s.name;
            }
        }
        return null;
    }
}
