package com.posts.config.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthclientDetailsRepository extends JpaRepository<OAuthClientDetails, String> {

}
