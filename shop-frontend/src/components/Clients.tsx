import { useEffect, useState } from "react";
import { getClients } from "../api/ClientApi";

const Clients = () => {
    const [clients, setClients] = useState<any[]>([]);

    useEffect(() => {
        const fetchClient = async () => {
            try {
                const data = await getClients();
                setClients(data);
            } catch (error) {
                console.error(error);
            }
        };
        fetchClient();
    }, []);

    return (
        <div style={{ maxWidth: "800px", margin: "auto", padding: "20px" }}>
            <h1>Clients</h1>

            {clients.length === 0 ? (
                <p>No clients found</p>
            ) : (
                <table style={{ width: "100%", borderCollapse: "collapse" }}>
                    <thead>
                        <tr>
                            <th style={thStyle}>ID</th>
                            <th style={thStyle}>Nom</th>
                            <th style={thStyle}>Username</th>
                            <th style={thStyle}>Email</th>
                            <th style={thStyle}>Role</th>
                            <th style={thStyle}>Fidelity Level</th>
                        </tr>
                    </thead>
                    <tbody>
                        {clients.map((client) => (
                            <tr key={client.id} style={trStyle}>
                                <td style={tdStyle}>{client.id}</td>
                                <td style={tdStyle}>{client.nom}</td>
                                <td style={tdStyle}>{client.username}</td>
                                <td style={tdStyle}>{client.email}</td>
                                <td style={tdStyle}>{client.role}</td>
                                <td style={tdStyle}>{client.niveau_fidelete}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

const thStyle: React.CSSProperties = {
    border: "1px solid #ccc",
    padding: "8px",
    backgroundColor: "#f5f5f5",
    textAlign: "left"
};

const tdStyle: React.CSSProperties = {
    border: "1px solid #ccc",
    padding: "8px"
};

const trStyle: React.CSSProperties = {
    borderBottom: "1px solid #eee"
};

export default Clients;
