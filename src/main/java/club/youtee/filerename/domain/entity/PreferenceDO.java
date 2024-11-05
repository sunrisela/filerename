package club.youtee.filerename.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Xinglong.Li
 * @date 2024-11-02
 */
@Getter
@Setter
@ToString
public class PreferenceDO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2595223517087560737L;

    private List<String> epRegs;
}
