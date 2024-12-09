package com.tksimeji.visualkit.lang;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;

public enum MinecraftLocale {
    /**
     * This enum represents all the locales supported by Minecraft.
     * I have made every effort to code with the utmost respect for all languages and regions. However, if there are any mistakes, I would be grateful if you could report them via GitHub Issues.
     * Additionally, please note that these are simply arranged in alphabetical order and there is no special intention behind their order.
     */

    AF_ZA("af", "za"), // Afrikaans
    AR_SA("ar", "sa"), // Arabic
    AST_ES("ast", "es"), // Asturian
    AZ_AZ("az", "az"), //Azerbaijani
    BA_RU("ba", "ru"), // Bashkir
    BAR("bar"), // Bavarian
    BE_BY("be", "by"), // Belarusian (Cyrillic)
    BE_LATN("be", "latn"), // Belarusian (Latin)
    BG_BG("bg", "bg"), // Bulgarian
    BR_FR("br", "fr"), // Breton
    BRB("brb"), // Brabantian
    BS_BA("bs", "ba"), // Bosnian
    CA_ES("ca", "es"), // Catalan
    CS_CZ("cs", "cz"), // Czech
    CY_GB("cy", "gb"), // Welsh
    DA_DK("da", "dk"), // Danish
    DE_AT("de", "at"), // Austrian German
    DE_CH("de", "ch"), // Swiss German
    DE_DE("de", "de"), //German
    EL_GR("el", "gr"), // Greek
    EN_AU("en", "au"), // Australian English
    EN_CA("en", "ca"), // Canadian English
    EN_GB("en", "gb"), // British English
    EN_NZ("en", "nz"), // New Zealand English
    EN_PT("en", "pt"), // Pirate English
    EN_UD("en", "ud"), // Upside down British English
    EN_US("en", "us"), // American English
    ENP("enp"), // Modern English minus borrowed words
    ENWS("enws"), // Early Modern English
    EO_UY("eo", "uy"), // Esperanto
    ES_AR("es", "ar"), // Argentinian Spanish
    ES_CL("es", "cl"), // Chilean Spanish
    ES_EC("es", "ec"), // Ecuadorian Spanish
    ES_ES("es", "es"), // European Spanish
    ES_MX("es", "mx"), // Mexican Spanish
    ES_UY("es", "uy"), // Uruguayan Spanish
    ES_VE("es", "ve"), // Venezuelan Spanish
    ESAN("esan"), // Andalusian
    ET_EE("et", "ee"), // Estonian
    EU_ES("eu", "es"), // Basque
    FA_IR("fa", "ir"), // Persian
    FI_FI("fi", "fi"), // Finnish
    FIL_PH("fil", "ph"), // Filipino
    FO_FO("fo", "fo"), // Faroese
    FR_CA("fr", "ca"), // Canadian French
    FR_FR("fr", "fr"), // European French
    FRA_DE("fra", "de"), // East Franconian
    FUR_IT("fur", "it"), // Friulian
    FY_NL("fy", "nl"), // Frisian
    GA_IE("ga", "ie"), // Irish
    GD_GB("gd", "gb"), // Scottish Gaelic
    GL_ES("gl", "es"), // Galician
    HAW_US("haw", "us"), // Hawaiian
    HE_IL("he", "il"), // Hebrew
    HI_IN("hi", "in"), // Hindi
    HN_NO("hn", "no"), // High Norwegian
    HR_HR("hr", "hr"), // Croatian
    HU_HU("hu", "hu"), // Hungarian
    HY_AM("ay", "am"), // Armenian
    ID_ID("id", "id"), // Indonesian
    IG_NG("ig", "ng"), // Igbo
    IO_EN("io", "en"), // Ido
    IS_IS("is", "is"), // Icelandic
    ISV("isv"), // Interslavic
    IT_IT("it", "it"), // Italian
    JA_JP("ja", "jp"), // Japanese
    JBO_EN("jbo", "en"), // Lojban
    KA_GE("ka", "ge"), // Georgian
    KK_KZ("kk", "kz"), // Kazakh
    KN_IN("kn", "in"), // Kannada
    KO_KR("ko", "kr"), // Korean
    KSH("ksh"), // Kölsch/Ripuarian
    KW_GB("kw", "gb"), // Comish
    LA_LA("la", "la"), // Latin
    LB_LU("lb", "lu"), // Luxembourgish
    LMO("lmo"), // Lombard
    LO_LA("lo", "la"), // Lao
    LOL_US("lol", "us"), // LOLCAT
    LT_LT("lt", "lt"), // Lithuanian
    LV_LV("lv", "lv"), // Latvian
    LZH("lzh"), // Literary Chinese
    MK_MK("mk", "mk"), // Macedonian
    MN_MN("mn", "mn"), // Mongolian
    MS_MY("ms", "my"), // Malay
    MT_MT("mt", "mt"), // Maltese
    NAH("nah"), // Nahuatl
    NDS_DE("nds", "de"), // Low German
    NL_BE("nl", "be"), // Dutch, Flemish
    NL_NL("nl", "nl"), // Dutch
    NN_NO("nn", "no"), // Norwegian Nynorsk
    NO_NO("no", "no"), // Norwegian Bokmål
    OC_FR("oc", "fr"), // Occitan
    OVD("ovd"), // Elfdalian
    PL_PL("pl", "pl"), // Polish
    PLS("pls"), // Popoloca
    PT_BR("pt", "br"), // Brazilian Portuguese
    PT_PT("pt", "pt"), // European Portuguese
    QYA_AA("qya", "aa"), // Quenya (Form of Elvish from LOTR)
    RO_RO("ro", "ro"), // Romanian
    RPR("rpr"), // Russian (Pre-revolutionary)
    RU_RU("ru", "ru"), // Russian
    RY_UA("ry", "ua"), // Rusyn
    SAH_SAH("sah", "sah"), // Yakut
    SE_NO("se", "no"), // Northern Sami
    SK_SK("sk", "sk"), // Slovak
    SL_SI("sl", "si"), // Slovenian
    SO_SO("so", "so"), // Somali
    SQ_AL("sq", "al"), // Albanian
    SR_CS("sr", "cs"), // Serbian (Latin)
    SR_SP("sr", "sp"), // Serbian (Cyrillic)
    SV_SE("sv", "se"), // Swedish
    SXU("sxu"), // Upper Saxon German
    SZL("szl"), // Silesian
    TA_IN("ta", "in"), // Tamil
    TH_TH("th", "th"), // Thai
    TL_PH("tl", "ph"), // Tagalog
    TLH_AA("tlh", "aa"), // Klingon
    TOK("tok"), // Toki Pona
    TR_TR("tr", "tr"), // Turkish
    TT_RU("tt", "ru"), // Tatar
    TZO_MX("tzo", "mz"), // Tzotzil
    UK_UA("uk", "ua"), // Ukrainian
    VAL_ES("val", "es"), // Valencian
    VEC_IT("vec", "it"), // Venetian
    VI_VN("vi", "vn"), // Vietnamese
    VP_VL("vp_vl"), // Viossa
    YI_DE("yi", "de"), // Yiddish
    YO_NG("yo", "ng"), // Yoruba
    ZH_CN("zh", "zn"), // Chinese Simplified (Mainland China; Mandarin)
    ZH_HK("zh", "hk"), // Chinese Traditional (Hong Kong SAR; Mix)
    ZH_TW("zh", "tw"), // Chinese Traditional (Taiwan; Mandarin)
    ZLM_ARAB("zlm", "arab"); // Malay (Jawi)

    public static @Nullable MinecraftLocale of(@NotNull Locale locale) {
        return Arrays.stream(values()).filter(i -> i.asLocale().equals(locale)).findFirst().orElse(null);
    }

    public static @Nullable MinecraftLocale of(@NotNull CommandSender player) {
        return of(player instanceof Player p ? p.locale() : EN_US.asLocale());
    }

    public static @Nullable MinecraftLocale of(@NotNull String tag) {
        return Arrays.stream(values()).filter(i -> i.toString().equals(tag)).findFirst().orElse(null);
    }

    private final @NotNull String lang;
    private final @Nullable String region;

    MinecraftLocale(@NotNull String lang, @Nullable String region) {
        this.lang = lang;
        this.region = region;
    }

    MinecraftLocale(@NotNull String lang) {
        this(lang, null);
    }

    public @NotNull String getLang() {
        return lang;
    }

    public @Nullable String getRegion() {
        return region;
    }

    public @NotNull Locale asLocale() {
        return region != null ? Locale.of(lang, region) : Locale.of(lang);
    }

    @Override
    public @NotNull String toString() {
        return region != null ? lang + "_" + region : lang;
    }
}
