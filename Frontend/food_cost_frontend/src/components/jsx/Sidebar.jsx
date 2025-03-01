import React from "react";
import '../css/Sidebar.css'
import ProfileButton from "../jsx/ProfileButton";
import SidebarButton from "./SidebarButton";
import urlImageRestaurant from'../../assets/restaurant.png'
import rawMaterial from '../../assets/rawMaterial.png'
import users from '../../assets/users.png'
import graph from '../../assets/graph.png'
import info from '../../assets/info.png'
import image from '../../assets/logo-svg.svg'



export default function Sidebar( { onPressList, onPressAdd, onPressRaw, onPressGraph, onPressInfo }) {
    


    return (
        <>
        <div id="containerSidebar">
                <div id="sidebar" >
                    <SidebarButton urlImage={image} text={"Food Cost"} />

                    <ul id="menuList">
                        <li>
                            <SidebarButton urlImage={users} text={"Lista ristoranti"} onPress={onPressList}/>
                        </li>

                        <li>               
                            <SidebarButton urlImage={urlImageRestaurant} text={"Aggiugi ristorante"} onPress={onPressAdd}/>
                        </li>

                        <li> 
                            <SidebarButton urlImage={rawMaterial} text={"Materie prime"} onPress={onPressRaw}/>
                        </li>

                        <li> 
                            <SidebarButton urlImage={graph} text={"Grafici vendite"} onPress={onPressGraph}/>
                        </li>
                    </ul>

                    <SidebarButton urlImage={info} text={"Informazioni"} onPress={onPressInfo}/>
                </div>
                <div id="hiddenDiv"></div>

            </div>
        </>
    )
}