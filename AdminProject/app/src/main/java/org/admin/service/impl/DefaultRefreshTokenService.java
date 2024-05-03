package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.RefreshToken;
import org.admin.repository.RefreshTokenDao;
import org.admin.service.RefreshTokenService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultRefreshTokenService implements RefreshTokenService {
    private final RefreshTokenDao refreshTokenDao;
    @Override
    public void addRefreshToken(RefreshToken refreshToken) {
        refreshTokenDao.addRefreshToken(refreshToken);
    }

    @Override
    public int deleteRefreshToken(String refreshToken) {
        return refreshTokenDao.deleteRefreshToken(refreshToken);
    }

    @Override
    public RefreshToken findRefreshToken(String refreshToken) {
        return refreshTokenDao.findRefreshToken(refreshToken);
    }
}
