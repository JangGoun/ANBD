import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import './CSS/Header.css';

const StyledHeader = styled.header`
  /* 다른 스타일들... */
  #menuIcon {
    display: none;
    
  }

  @media screen and (max-width: 768px) {
    #menuIcon {
      display: inline-block;
      width: 30px;
    }
  }
`;

const NavMenu = styled.ul`
  /* 다른 스타일들... */
  display: ${(props) => (props.isMenuOpen ? 'block' : 'none')};
  flex-direction: column;
  align-items: center;
  width: 100%;

  @media screen and (max-width: 768px) {
    display: ${(props) => (props.isMenuOpen ? 'block' : 'none')};
  }
`;

const Header = () => {
    const navigate = useNavigate();

    const [isMenuOpen, setIsMenuOpen] = useState(false);
    // 모든 대분류의 중분류가 따로따로 토글되도록 아래 상태함수들 줌
    const [isBclothingOpen, setIsBclothingOpen] = useState(false);
    const [isBfoodOpen, setIsBfoodOpen] = useState(false);
    const [isBlivingOpen, setIsBlivingOpen] = useState(false);

    const toggleMenu = () => {
        setIsMenuOpen(!isMenuOpen);
        // 햄버거 토글하면 대분류 모두 닫기
        setIsBclothingOpen(false);
        setIsBfoodOpen(false);
        setIsBlivingOpen(false);
    };

    const toggleClothing = () => {
        setIsBclothingOpen(!isBclothingOpen);
        // 의류 토글 시 다른 메뉴는 닫아!!!
        setIsBfoodOpen(false);
        setIsBlivingOpen(false);
    };

    const toggleFood = () => {
        setIsBfoodOpen(!isBfoodOpen);
        // 식품 토글 시 다른 메뉴는 딷아!!!!
        setIsBclothingOpen(false);
        setIsBlivingOpen(false);
    };

    const toggleLiving = () => {
        setIsBlivingOpen(!isBlivingOpen);
        // 생활용품 토글 시 다른 메뉴는 닫아!!!!
        setIsBclothingOpen(false);
        setIsBfoodOpen(false);
    };

    return (
        <div className="header">
            <StyledHeader>
                <div className='header_fix'>
                <div className="nav_logo">
                    {/* 아이콘 클릭 시 메뉴 토글 */}
                    <img src="icon/menu.png" id="menuIcon" onClick={toggleMenu} />
                    AH!NaBaDa
                </div>
                <div className="mypage">
                        <img src="icon/usericon.png" id="mypagebtn" />
                </div>
                </div>

                <NavMenu isMenuOpen={isMenuOpen}>
                    <ul onClick={toggleClothing}>
                        의류
                        {isBclothingOpen && (
                            <ul className="submenu">
                                <li onClick={() => navigate()}>여성의류</li>
                                <li onClick={() => navigate()}>남성의류</li>
                                <li onClick={() => navigate()}>아동의류</li>
                            </ul>
                        )}
                    </ul>
                    <ul onClick={toggleFood}>
                        식품
                        {isBfoodOpen && (
                            <ul className="submenu">
                                <li onClick={() => navigate()}>1</li>
                                <li onClick={() => navigate()}>2</li>
                                <li onClick={() => navigate()}>3</li>
                            </ul>
                        )}
                    </ul>
                    <ul onClick={toggleLiving}>
                        생활용품
                        {isBlivingOpen && (
                            <ul className="submenu">
                                <li onClick={() => navigate()}>1</li>
                                <li onClick={() => navigate()}>2</li>
                                <li onClick={() => navigate()}>3</li>
                            </ul>
                        )}
                    </ul>
                </NavMenu>

            </StyledHeader>
        </div>
    );
};

export default Header;