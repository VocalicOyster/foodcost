import React, { useEffect, useState } from "react"
import { useNavigate } from 'react-router-dom';
import '../css/AdminHome.css'
import Sidebar from "./Sidebar";
import MainMenu from "./MainMenu";
import { jwtDecode } from "jwt-decode";


export default function AdminHome() {
    const navigate = useNavigate()
    const [prova, setProva] = useState(false)

    useEffect(() => {
        const jwt = sessionStorage.getItem("jwt");

        if(jwt) {
            const decoded = jwtDecode(jwt);
            const exp = new Date(decoded.exp * 1000)
            const now = new Date()
            if(now > exp) {
                navigate("/")
            }
        } else {
            navigate("/")
        }
    }, [navigate]);

    return (
        <>            
            <div id="containerAdmin">
                <Sidebar />
                <MainMenu />
            </div>
        </>
    )
    
}