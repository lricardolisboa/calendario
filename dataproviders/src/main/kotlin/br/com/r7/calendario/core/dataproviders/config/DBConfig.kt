package br.com.r7.calendario.core.dataproviders.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["br.com.r7.calendario.core.dataproviders.entities"])
@EnableJpaRepositories(basePackages = ["br.com.r7.calendario.core.dataproviders.repository"])
class DBConfig