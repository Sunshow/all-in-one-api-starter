package com.example.api.provider.jpa.repository.database

import net.sunshow.toolkit.core.qbean.helper.entity.BaseEntity
import nxcloud.foundation.core.data.jpa.entity.DefaultJpaEntity
import nxcloud.foundation.core.data.jpa.entity.DeletedField
import nxcloud.foundation.core.data.jpa.entity.JpaEntity
import nxcloud.foundation.core.data.jpa.entity.SoftDeleteJpaEntity
import javax.persistence.MappedSuperclass

interface IQEntity : BaseEntity

@MappedSuperclass
abstract class EmptyQEntity : JpaEntity(), IQEntity

@MappedSuperclass
abstract class QEntity : DefaultJpaEntity(), IQEntity

@MappedSuperclass
abstract class SoftDeleteQEntity : SoftDeleteJpaEntity(), DeletedField, IQEntity