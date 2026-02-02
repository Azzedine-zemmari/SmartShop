import { Routes, Route } from "react-router-dom";
import Clients from "./components/Clients";


const AppRouter = () => {
    return(
        <Routes>
            <Route path="/clients" element={<Clients />} />
        </Routes>
    )
}

export default AppRouter;