package com.example.api.provider.jpa.repository.database

import net.sunshow.toolkit.core.qbean.helper.repository.BaseRepository
import nxcloud.foundation.core.data.jpa.repository.EmptyJpaRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository
import java.io.Serializable

/**
 * author: sunshow.
 */
@NoRepositoryBean
interface QRepository<T, ID : Serializable> :
    BaseRepository<T, ID>,
    EmptyJpaRepository<T, ID>,
    PagingAndSortingRepository<T, ID>