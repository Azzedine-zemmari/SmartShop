import { Routes, Route } from "react-router-dom";
import Clients from "./components/Clients";
import Login from "./components/login";


const AppRouter = () => {
    return(
        <Routes>
            <Route path="/" element={<Clients />} />
            <Route path="/login" element={<Login />} />
        </Routes>
    )
}

export default AppRouter;