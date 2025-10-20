package org.mmocore.gameserver.utils.ai;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : iRock
 * @date : 22.08.18
 * <p/>
 * Конвертирует id скилла в птс формат.
 */
@Target(ElementType.FIELD)
// Target field
@Retention(RetentionPolicy.RUNTIME)
public @interface SkillInfo {
    /**
     * @return - строка - индикатор начала элемента
     */
    int id();

    int level();
}
