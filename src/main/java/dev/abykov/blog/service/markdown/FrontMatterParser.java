package dev.abykov.blog.service.markdown;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.util.Collections;
import java.util.Map;

/**
 * Parses YAML front matter from a Markdown file into a key-value map.
 */
@Component
public class FrontMatterParser {

    private final Yaml yaml = new Yaml();

    /**
     * Parses YAML front matter text into a map.
     *
     * @param yamlText raw YAML text between '---' delimiters
     * @return parsed map of key-value pairs, or an empty map if YAML is empty
     * @throws IllegalArgumentException if YAML is invalid or cannot be parsed
     */
    public Map<String, Object> parse(String yamlText) {
        if (yamlText == null || yamlText.isBlank()) {
            return Collections.emptyMap();
        }

        try {
            Object result = yaml.load(yamlText);
            if (result instanceof Map<?, ?> map) {
                //noinspection unchecked
                return (Map<String, Object>) map;
            } else {
                throw new IllegalArgumentException("Front matter must be a YAML mapping (key-value pairs)");
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse front matter: " + e.getMessage(), e);
        }
    }
}
