package com.zhang.commons;

public class PornIndexConstants {
    public static final String MAPPING_TEMPLATE="{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \n" +
            "      \n" +
            "      \"title\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"actress\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"subline\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "        \n" +
            "      },\n" +
            "      \"magnet\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"num\": {\n" +
            "        \"type\": \"keyword\",\n" +
            "        \n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"types\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"date\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "        \n" +
            "      },\n" +
            "      \"producer\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"hd\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      }\n" +
            "        \n" +
            "      \n" +
            "      \n" +
            "    }\n" +
            "  }\n" +
            "}";
}
