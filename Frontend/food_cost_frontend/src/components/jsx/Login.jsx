import React, { useState } from "react"
import "../css/Login.css"
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";



export default function Login() {

    const [user, setUser] = useState({username: "", password: ""})
    const [wrong, setWrong] = useState(false)

    const navigate = useNavigate();


    const usernameChange = (e) => {
        setUser({
        ...user,
        username: e.target.value,
        });
    };

    const passwordChange = (e) => {
        setUser({
        ...user,
        password: e.target.value,
        });
    };

    function onMouseOverPassword() {
    let passwordInput = document.getElementById("passwordInput")
    passwordInput.style.backgroundColor = "#E8EDE8"
    passwordInput.style.boxShadow = "-2px 1px -0 orange"
    }

    function onMouseOutPassword() {
        let passwordInput = document.getElementById("passwordInput")
        passwordInput.style.backgroundColor = "white"
        passwordInput.style.boxShadow = "none"    
    }

    function onMouseOverUsername() {
        let passwordInput = document.getElementById("usernameInput")
        passwordInput.style.backgroundColor = "#E8EDE8"
        passwordInput.style.boxShadow = "-2px 1px -0 orange"
    }

    function onMouseOutUsername() {
        let usernameInput = document.getElementById("usernameInput")
        usernameInput.style.backgroundColor = "white"
        usernameInput.style.boxShadow = "none"
    }

    function onFocusUsername() {
        let usernameInput = document.getElementById("usernameInput")
        let passwordInput = document.getElementById("passwordInput")
        usernameInput.style.outline = "1px solid orange"
        usernameInput.style.border = "none"
        passwordInput.style.border = "none"
        setWrong(false)
    }
    function onBlurUsername() {
        let usernameInput = document.getElementById("usernameInput")
        usernameInput.style.outline = "none"
    }
    
    function onFocusPassword() {
        let passwordInput = document.getElementById("passwordInput")
        let usernameInput = document.getElementById("usernameInput")
        passwordInput.style.outline = "1px solid orange"
        usernameInput.style.border = "none"
        setWrong(false)
    }
    function onBlurPassword() {
        let passwordInput = document.getElementById("passwordInput")
        passwordInput.style.outline = "none"
    }

    function onSendOver() {
        let sendButton = document.getElementById("send_button")
        sendButton.style.filter = "brightness(95%)"
    }

    function onSendOut() {
        let sendButton = document.getElementById("send_button")
        sendButton.style.filter = "brightness(100%)"
    }


    const INDIRIZZO = "http://localhost:8080/auth/login"

    const login = (e) => {
        e.preventDefault()
        fetch(INDIRIZZO, {
            method: "POST",
            headers: new Headers({
                "Content-Type": "application/json"
            }),
            body: JSON.stringify({
                "username": user.username,
                "password": user.password
            })
        })
        .then((response) => response.json())
        .then((data) => {
            if(data.status == 200) {
                sessionStorage.setItem("jwt", data.data);
                const decoded = jwtDecode(data.data)
                switch(decoded.role) {
                    case "ADMIN":
                        navigate("/admin/home")
                        break;
                    case "OWNER":
                        navigate("/owner/home")
                        break;
                    case "EMPLOYEE":
                        navigate("/employee/home")
                        break;
                }
                        
            }
            if(data.status == 404) {
                setWrong(true)
                const usernameInput = document.getElementById("usernameInput")
                const passwordInput = document.getElementById("passwordInput")
                usernameInput.style.border = ("1px solid red")
                passwordInput.style.border = ("1px solid red")
            }
        })
    }


    return (
        <div id="background">
            <div id="container">
                <form id="form" onSubmit={login} >
                    <div id="up_container">
                        <img src="src/assets/logo-transparent.png" alt="logo" id="logo"/>
                        <h1 style={{margin: "0px"}}>Bentornato</h1>
                    </div>

                    <div id="bottom_container">
                        {wrong && <p id="wrongPass">Username o password errati</p>}
                        <label htmlFor="username" id="username_label" >Il tuo username</label>
                        <input type="text" id="usernameInput" name="username" placeholder="Username" onChange={usernameChange} onBlur={onBlurUsername} onFocus={onFocusUsername} onMouseOver={onMouseOverUsername} onMouseOut={onMouseOutUsername}/>
                        <br />
                        <br />
                        <label htmlFor="password" id="password_label" >La tua password</label>
                        <input type="password" id="passwordInput" name="password" placeholder="Password" onChange={passwordChange} onFocus={onFocusPassword} onBlur={onBlurPassword} onMouseOver={onMouseOverPassword} onMouseOut={onMouseOutPassword} />
                        <br />
                        <button id="send_button" type="submit"  onMouseOver={onSendOver} onMouseOut={onSendOut}> INVIA </button>
                    </div>
                </form>
            </div>
        </div>
    )
}
