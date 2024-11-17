package com.org;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class loadJson {

    public static Pelea cargarDeJson() throws IOException {
        String response = "{\n" +
                "  \"pokemon1\" : {\n" +
                "    \"nombre\" : \"suicune\",\n" +
                "    \"imagen\" : \"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/back/245.gif\",\n" +
                "    \"movimientos\" : [ {\n" +
                "      \"nombre\" : \"hyper-beam\",\n" +
                "      \"accuracy\" : 90,\n" +
                "      \"power\" : 150,\n" +
                "      \"pp\" : 5,\n" +
                "      \"priority\" : 0,\n" +
                "      \"tipo\" : \"normal\"\n" +
                "    }, {\n" +
                "      \"nombre\" : \"iron-tail\",\n" +
                "      \"accuracy\" : 75,\n" +
                "      \"power\" : 100,\n" +
                "      \"pp\" : 15,\n" +
                "      \"priority\" : 0,\n" +
                "      \"tipo\" : \"steel\"\n" +
                "    }, {\n" +
                "      \"nombre\" : \"swagger\",\n" +
                "      \"accuracy\" : 85,\n" +
                "      \"power\" : -2,\n" +
                "      \"pp\" : 15,\n" +
                "      \"priority\" : 0,\n" +
                "      \"tipo\" : \"normal\"\n" +
                "    }, {\n" +
                "      \"nombre\" : \"tera-blast\",\n" +
                "      \"accuracy\" : 100,\n" +
                "      \"power\" : 80,\n" +
                "      \"pp\" : 10,\n" +
                "      \"priority\" : 0,\n" +
                "      \"tipo\" : \"normal\"\n" +
                "    } ],\n" +
                "    \"estadisticas\" : {\n" +
                "      \"hp\" : 100,\n" +
                "      \"attack\" : 75,\n" +
                "      \"defense\" : 115,\n" +
                "      \"specialAttack\" : 90,\n" +
                "      \"specialDefense\" : 115,\n" +
                "      \"speed\" : 85\n" +
                "    }\n" +
                "  },\n" +
                "  \"pokemon2\" : {\n" +
                "    \"nombre\" : \"hatterene\",\n" +
                "    \"imagen\" : \"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/858.gif\",\n" +
                "    \"movimientos\" : [ {\n" +
                "      \"nombre\" : \"wonder-room\",\n" +
                "      \"accuracy\" : -2,\n" +
                "      \"power\" : -2,\n" +
                "      \"pp\" : 10,\n" +
                "      \"priority\" : 0,\n" +
                "      \"tipo\" : \"psychic\"\n" +
                "    }, {\n" +
                "      \"nombre\" : \"facade\",\n" +
                "      \"accuracy\" : 100,\n" +
                "      \"power\" : 70,\n" +
                "      \"pp\" : 20,\n" +
                "      \"priority\" : 0,\n" +
                "      \"tipo\" : \"normal\"\n" +
                "    }, {\n" +
                "      \"nombre\" : \"life-dew\",\n" +
                "      \"accuracy\" : -2,\n" +
                "      \"power\" : -2,\n" +
                "      \"pp\" : 10,\n" +
                "      \"priority\" : 0,\n" +
                "      \"tipo\" : \"water\"\n" +
                "    }, {\n" +
                "      \"nombre\" : \"psychic-terrain\",\n" +
                "      \"accuracy\" : -2,\n" +
                "      \"power\" : -2,\n" +
                "      \"pp\" : 10,\n" +
                "      \"priority\" : 0,\n" +
                "      \"tipo\" : \"psychic\"\n" +
                "    } ],\n" +
                "    \"estadisticas\" : {\n" +
                "      \"hp\" : 100,\n" +
                "      \"attack\" : 75,\n" +
                "      \"defense\" : 115,\n" +
                "      \"specialAttack\" : 90,\n" +
                "      \"specialDefense\" : 115,\n" +
                "      \"speed\" : 85\n" +
                "    }\n" +
                "  }\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        Pelea p = mapper.treeToValue(root, Pelea.class);
        return p;
    }
}
