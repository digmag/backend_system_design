package ru.hits.common.security.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SecurityIntegrationProps {
    private String apiKey;
    private String rootPath;
}
