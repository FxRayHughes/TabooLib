package io.izzel.taboolib.util.chat;

import com.google.common.base.Preconditions;

import java.awt.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Simplistic enumeration of all supported color values for chat.
 */
public final class ChatColor {

    /**
     * The special character which prefixes all chat colour codes. Use this if
     * you need to dynamically convert colour codes from your custom format.
     */
    public static final char COLOR_CHAR = '\u00A7';
    public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx";
    /**
     * Pattern to remove all colour codes.
     */
    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + COLOR_CHAR + "[0-9A-FK-ORX]");
    /**
     * Colour instances keyed by their active character.
     */
    private static final Map<Character, ChatColor> BY_CHAR = new HashMap<Character, ChatColor>();
    /**
     * Colour instances keyed by their name.
     */
    private static final Map<String, ChatColor> BY_NAME = new HashMap<String, ChatColor>();
    /**
     * Represents black.
     */
    public static final ChatColor BLACK = new ChatColor('0', "black");
    /**
     * Represents dark blue.
     */
    public static final ChatColor DARK_BLUE = new ChatColor('1', "dark_blue");
    /**
     * Represents dark green.
     */
    public static final ChatColor DARK_GREEN = new ChatColor('2', "dark_green");
    /**
     * Represents dark blue (aqua).
     */
    public static final ChatColor DARK_AQUA = new ChatColor('3', "dark_aqua");
    /**
     * Represents dark red.
     */
    public static final ChatColor DARK_RED = new ChatColor('4', "dark_red");
    /**
     * Represents dark purple.
     */
    public static final ChatColor DARK_PURPLE = new ChatColor('5', "dark_purple");
    /**
     * Represents gold.
     */
    public static final ChatColor GOLD = new ChatColor('6', "gold");
    /**
     * Represents gray.
     */
    public static final ChatColor GRAY = new ChatColor('7', "gray");
    /**
     * Represents dark gray.
     */
    public static final ChatColor DARK_GRAY = new ChatColor('8', "dark_gray");
    /**
     * Represents blue.
     */
    public static final ChatColor BLUE = new ChatColor('9', "blue");
    /**
     * Represents green.
     */
    public static final ChatColor GREEN = new ChatColor('a', "green");
    /**
     * Represents aqua.
     */
    public static final ChatColor AQUA = new ChatColor('b', "aqua");
    /**
     * Represents red.
     */
    public static final ChatColor RED = new ChatColor('c', "red");
    /**
     * Represents light purple.
     */
    public static final ChatColor LIGHT_PURPLE = new ChatColor('d', "light_purple");
    /**
     * Represents yellow.
     */
    public static final ChatColor YELLOW = new ChatColor('e', "yellow");
    /**
     * Represents white.
     */
    public static final ChatColor WHITE = new ChatColor('f', "white");
    /**
     * Represents magical characters that change around randomly.
     */
    public static final ChatColor MAGIC = new ChatColor('k', "obfuscated");
    /**
     * Makes the text bold.
     */
    public static final ChatColor BOLD = new ChatColor('l', "bold");
    /**
     * Makes a line appear through the text.
     */
    public static final ChatColor STRIKETHROUGH = new ChatColor('m', "strikethrough");
    /**
     * Makes the text appear underlined.
     */
    public static final ChatColor UNDERLINE = new ChatColor('n', "underline");
    /**
     * Makes the text italic.
     */
    public static final ChatColor ITALIC = new ChatColor('o', "italic");
    /**
     * Resets all previous chat colors or formats.
     */
    public static final ChatColor RESET = new ChatColor('r', "reset");
    /**
     * This colour's colour char prefixed by the {@link #COLOR_CHAR}.
     */
    private final String toString;
    private final String name;

    private ChatColor(char code, String name) {
        this.name = name;
        this.toString = new String(new char[]
                {
                        COLOR_CHAR, code
                });

        BY_CHAR.put(code, this);
        BY_NAME.put(name.toUpperCase(Locale.ROOT), this);
    }

    private ChatColor(String name, String toString) {
        this.name = name;
        this.toString = toString;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.toString);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ChatColor other = (ChatColor) obj;

        return Objects.equals(this.toString, other.toString);
    }

    @Override
    public String toString() {
        return toString;
    }

    /**
     * Strips the given message of all color codes
     *
     * @param input String to strip of color
     * @return A copy of the input string, without any coloring
     */
    public static String stripColor(final String input) {
        if (input == null) {
            return null;
        }

        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && ALL_CODES.indexOf(b[i + 1]) > -1) {
                b[i] = ChatColor.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    /**
     * Get the colour represented by the specified code.
     *
     * @param code the code to search for
     * @return the mapped colour, or null if non exists
     */
    public static ChatColor getByChar(char code) {
        return BY_CHAR.get(code);
    }

    public static ChatColor of(Color color) {
        return of("#" + Integer.toHexString(color.getRGB()).substring(2));
    }

    public static ChatColor of(String string) {
        Preconditions.checkArgument(string != null, "string cannot be null");
        if (string.startsWith("#") && string.length() == 7) {
            try {
                Integer.parseInt(string.substring(1), 16);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Illegal hex string " + string);
            }

            StringBuilder magic = new StringBuilder(COLOR_CHAR + "x");
            for (char c : string.substring(1).toCharArray()) {
                magic.append(COLOR_CHAR).append(c);
            }

            return new ChatColor(string, magic.toString());
        }

        ChatColor defined = BY_NAME.get(string.toUpperCase(Locale.ROOT));
        if (defined != null) {
            return defined;
        }

        throw new IllegalArgumentException("Could not parse ChatColor " + string);
    }

    /**
     * See {@link Enum#valueOf(Class, String)}.
     *
     * @param name color name
     * @return ChatColor
     * @deprecated holdover from when this class was an enum
     */
    @Deprecated
    public static ChatColor valueOf(String name) {
        Preconditions.checkNotNull(name, "Name is null");

        ChatColor defined = BY_NAME.get(name);
        Preconditions.checkArgument(defined != null, "No enum constant " + ChatColor.class.getName() + "." + name);

        return defined;
    }

    /**
     * Get an array of all defined colors and formats.
     *
     * @return copied array of all colors and formats
     * @deprecated holdover from when this class was an enum
     */
    @Deprecated
    public ChatColor[] values() {
        return BY_CHAR.values().toArray(new ChatColor[BY_CHAR.values().size()]);
    }

    /**
     * See {@link Enum#name()}.
     *
     * @return constant name
     * @deprecated holdover from when this class was an enum
     */
    @Deprecated
    public String name() {
        return getName().toUpperCase(Locale.ROOT);
    }

    public String getName() {
        return this.name;
    }
}
