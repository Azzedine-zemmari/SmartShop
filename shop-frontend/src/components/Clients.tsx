import { useEffect, useState } from "react";
import { getClients } from "../api/ClientApi";

const Clients = () => {
    const [clients , setClients] = useState([]);
    useEffect(()=> {
        const fetchClient = async () => {
            try{
                const data = await getClients();
                setClients(data);
                
            }catch(error){
                console.error(error)
            }
        }
        fetchClient();
    },[])
    return (
        <div>
            <h1>Clients</h1>

            {clients.length === 0 ? (
                <p>No clients found</p>
            ) : (
                <ul>
                    {clients.map((client) => (
                        <li key={client.id}>
                            {client.name} - {client.email}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default Clients; 