package com.wooin.hahahaback.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
// 해당 태그를 Application에 추가하는 것이 아니라 따로 빼둠으로서, 컨트롤러 단위테스트시 충돌을 피한다.
@EnableJpaAuditing
public class JpaConfig {
}
