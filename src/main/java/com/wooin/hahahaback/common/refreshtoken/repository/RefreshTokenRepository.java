package com.wooin.hahahaback.common.refreshtoken.repository;

import com.wooin.hahahaback.common.refreshtoken.dto.TokenDto;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<TokenDto, Long> {
}
