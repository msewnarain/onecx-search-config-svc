package org.onecx.search.config.domain.dao;

import static org.onecx.search.config.domain.dao.SearchConfigDAO.SearchTemplateErrorKey.FIND_SEARCH_CONFIGS_BY_CRITERIA_FAILED;
import static org.onecx.search.config.domain.dao.SearchConfigDAO.SearchTemplateErrorKey.FIND_SEARCH_CONFIG_FAILED;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import org.onecx.search.config.domain.models.SearchConfig;
import org.onecx.search.config.domain.models.SearchConfig_;
import org.tkit.quarkus.jpa.daos.AbstractDAO;
import org.tkit.quarkus.jpa.daos.PageResult;
import org.tkit.quarkus.jpa.exceptions.DAOException;
import org.tkit.quarkus.jpa.models.AbstractTraceableEntity_;

import gen.io.github.onecx.search.config.v1.model.SearchConfigSearchRequestDTOV1;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class SearchConfigDAO extends AbstractDAO<SearchConfig> {

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = DAOException.class)
    public PageResult<SearchConfig> findByPage(String page) {

        try {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<SearchConfig> criteriaQuery = criteriaBuilder.createQuery(SearchConfig.class);
            Root<SearchConfig> searchConfigRoot = criteriaQuery.from(SearchConfig.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(searchConfigRoot.get(SearchConfig_.PAGE), page));

            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            criteriaQuery.distinct(true);

            criteriaQuery.orderBy(criteriaBuilder.desc(searchConfigRoot.get(AbstractTraceableEntity_.CREATION_DATE)));
            return createPageQuery(criteriaQuery, null).getPageResult();
        } catch (NoResultException noResultException) {
            return null;

        } catch (Exception exception) {
            throw new DAOException(FIND_SEARCH_CONFIG_FAILED, exception);
        }
    }

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = DAOException.class)
    public PageResult<SearchConfig> findBySearchCriteria(SearchConfigSearchRequestDTOV1 searchCriteria) {

        try {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<SearchConfig> criteriaQuery = criteriaBuilder.createQuery(SearchConfig.class);
            Root<SearchConfig> searchConfigRoot = criteriaQuery.from(SearchConfig.class);

            String application = searchCriteria.getApplication();
            String page = searchCriteria.getPage();
            String name = searchCriteria.getName();

            List<Predicate> predicates = new ArrayList<>();
            if (application != null && !application.isEmpty()) {
                predicates.add(criteriaBuilder.equal(searchConfigRoot.get(SearchConfig_.APPLICATION), application));
            }

            if (page != null && !page.isEmpty()) {
                predicates.add(criteriaBuilder.equal(searchConfigRoot.get(SearchConfig_.PAGE), page));
            }

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.equal(searchConfigRoot.get(SearchConfig_.NAME), name));
            }

            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

            criteriaQuery.orderBy(criteriaBuilder.desc(searchConfigRoot.get(AbstractTraceableEntity_.CREATION_DATE)));
            return createPageQuery(criteriaQuery, null).getPageResult();
            //  return getEntityManager().createQuery(criteriaQuery).getResultList();

        } catch (Exception exception) {
            throw new DAOException(FIND_SEARCH_CONFIGS_BY_CRITERIA_FAILED, exception);
        }
    }

    public enum SearchTemplateErrorKey {
        /**
         * The find default search template failed.
         */
        FIND_SEARCH_CONFIG_FAILED,
        /**
         * The find search templates by criteria failed.
         */
        FIND_SEARCH_CONFIGS_BY_CRITERIA_FAILED
    }

}