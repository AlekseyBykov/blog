package dev.abykov.blog.service.markdown;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

@Component
public class FrontMatterParser {

    private final Yaml yaml = new Yaml();

    public Map<String, Object> parse(String yamlText) {
        return yaml.load(yamlText);
    }
}
