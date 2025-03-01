import { Routes, Route } from 'react-router-dom'
import Login from './components/jsx/Login'
import AdminHome from './components/jsx/AdminHome'
import OwnerHome from './components/jsx/OwnerHome'
import EmployeeHome from './components/jsx/EmployeeHome'


const App = () => {
    return (
        <>
            <Routes>
                <Route  path='/' element={<Login />}/>
                <Route path='/admin/home' element={<AdminHome />} />
                <Route path='/owner/home' element={<OwnerHome />} />
                <Route path='/employee/home' element={<EmployeeHome />} />
            </Routes>
        </>
    )
}

export default App;