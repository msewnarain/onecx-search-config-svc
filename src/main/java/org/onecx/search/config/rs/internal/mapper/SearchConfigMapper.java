package org.onecx.search.config.rs.v1.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.onecx.search.config.domain.models.SearchConfig;
import org.tkit.quarkus.jpa.daos.PageResult;
import org.tkit.quarkus.rs.mappers.OffsetDateTimeMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gen.io.github.onecx.search.config.v1.model.ProblemDetailResponseDTOV1;
import gen.io.github.onecx.search.config.v1.model.SearchConfigDTOV1;
import gen.io.github.onecx.search.config.v1.model.SearchConfigPageResultDTOV1;

@Mapper(uses = OffsetDateTimeMapper.class)
public abstract class SearchConfigMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "apiVersion", ignore = true)
    @Mapping(target = "readOnly", source = "isReadOnly")
    @Mapping(target = "advanced", source = "isAdvanced")
    public abstract SearchConfig create(SearchConfigDTOV1 dto);

    public String map(Map<String, String> values) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(values);

    }

    @Mapping(target = "isReadOnly", source = "readOnly")
    @Mapping(target = "isAdvanced", source = "advanced")
    @Mapping(target = "removeColumnsItem", source = "advanced")
    @Mapping(target = "removeValuesItem", source = "advanced")
    public abstract SearchConfigDTOV1 map(SearchConfig searchConfig);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modificationCount", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "creationUser", ignore = true)
    @Mapping(target = "modificationUser", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "controlTraceabilityManual", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    @Mapping(target = "apiVersion", ignore = true)
    @Mapping(target = "readOnly", source = "isReadOnly")
    @Mapping(target = "advanced", source = "isAdvanced")
    public abstract SearchConfig update(@MappingTarget SearchConfig searchConfig, SearchConfigDTOV1 dto);

    public String map(List<String> value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(value);
    }

    public List<String> map(String value) throws JsonProcessingException {
        List<String> columns;
        ObjectMapper mapper = new ObjectMapper();
        columns = mapper.readValue(value, List.class);
        return columns;
    }

    public Map<String, String> mapValues(String value) throws JsonProcessingException {
        Map<String, String> values;

        ObjectMapper mapper = new ObjectMapper();

        values = mapper.readValue(value, HashMap.class);
        return values;
    }

    @Mapping(target = "params", ignore = true)
    @Mapping(target = "removeParamsItem", ignore = true)
    @Mapping(target = "invalidParams", ignore = true)
    @Mapping(target = "removeInvalidParamsItem", ignore = true)
    public abstract ProblemDetailResponseDTOV1 exception(String errorCode, String detail);

    @Mapping(target = "removeStreamItem", ignore = true)
    public abstract SearchConfigPageResultDTOV1 mapToPageResult(PageResult<SearchConfig> results);
}