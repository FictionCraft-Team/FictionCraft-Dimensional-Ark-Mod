package wintersteve25.dautils.common.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class DABaseItem extends Item {

    private static final Pattern COMPILE = Pattern.compile("@", Pattern.LITERAL);

    protected void addInformationLocalized(List<String> tooltip, String key, Object... parameters) {
        String translated = I18n.format(key, parameters);
        translated = COMPILE.matcher(translated).replaceAll("\u00a7");
        Collections.addAll(tooltip, StringUtils.split(translated, "\n"));
    }
}
