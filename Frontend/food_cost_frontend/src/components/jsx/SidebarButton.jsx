import React from "react";
import '../css/SidebarButton.css'



export default function SidebarButton( {urlImage, text, onPress} ) {
    return (
        <>
            <div id="buttonContainer" onClick={onPress}>
                <div id="buttonSidebarImageContainer">
                    <img src={urlImage} alt="prova" id="buttonImage"/>
                </div>
                
                <h1 id="sidebarButtonsText">{text}</h1>
            </div>
        </>
    )
}