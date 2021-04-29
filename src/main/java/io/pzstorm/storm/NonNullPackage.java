package io.pzstorm.storm;

import java.lang.annotation.*;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import javax.annotation.meta.TypeQualifierNickname;

@Documented
@TypeQualifierNickname
@TypeQualifierDefault({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Nonnull
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.CLASS)
public @interface NonNullPackage {
}
