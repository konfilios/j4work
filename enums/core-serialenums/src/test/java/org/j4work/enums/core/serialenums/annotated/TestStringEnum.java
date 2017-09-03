package org.j4work.enums.core.serialenums.annotated;


import org.j4work.enums.core.serialenums.annotated.SlugAnnotatedEnum.Slug;

/**
 * .
 */
enum TestStringEnum {
    @Slug("")
    CONST_EMPTY_STRING,

    @Slug("Word")
    CONST_ENGLISH_MIXED_CASE_WORD,

    @Slug("Two Words")
    CONST_ENGLISH_MIXED_CASE_WORDS_WITH_SPACE,

    @Slug("Two-Words")
    CONST_ENGLISH_MIXED_CASE_WORDS_WITH_HYPHEN
}
