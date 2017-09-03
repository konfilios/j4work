package jbogo.l10n.repos;

import jbogo.l10n.entities.L10nCountry;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 */
public interface L10nCountryRepo extends PagingAndSortingRepository<L10nCountry, String>
{
}
