import { useEffect, useState } from "react";
import { getClients, getClientInfo, deleteClient } from "../api/ClientApi";

const Clients = () => {
    const [clients, setClients] = useState<any[]>([]);
    const [selectedClient, setSelectedClient] = useState<any | null>(null);

    useEffect(() => {
        fetchClients();
    }, []);

    const fetchClients = async () => {
        try {
            const data = await getClients();
            setClients(data);
        } catch (error) {
            console.error(error);
        }
    };

    const handleShowInfo = async (id: number) => {
        try {
            const data = await getClientInfo(id);
            setSelectedClient(data);
        } catch (error) {
            console.error("Error fetching client info:", error);
        }
    };

    const handleDelete = async (id: number) => {
        if (!window.confirm("Are you sure you want to delete this client?")) return;

        try {
            await deleteClient(id);
            // Remove client from local state
            setClients((prev) => prev.filter((client) => client.id !== id));
            // Close selected client if it was deleted
            if (selectedClient?.id === id) setSelectedClient(null);
            alert("Client deleted successfully!");
        } catch (error) {
            console.error("Error deleting client:", error);
            alert("Failed to delete client.");
        }
    };

    return (
        <div style={{ maxWidth: "900px", margin: "auto", padding: "20px" }}>
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
                            <th style={thStyle}>Actions</th>
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
                                <td style={tdStyle}>
                                    <button onClick={() => handleShowInfo(client.id)} style={{ marginRight: "8px" }}>
                                        View Info
                                    </button>
                                    <button onClick={() => handleDelete(client.id)} style={{ color: "red" }}>
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}

            {selectedClient && (
                <div style={{ marginTop: "30px", padding: "20px", border: "1px solid #ccc" }}>
                    <h2>Client Info</h2>
                    <p><strong>ID:</strong> {selectedClient.id}</p>
                    <p><strong>Nom:</strong> {selectedClient.nom}</p>
                    <p><strong>Username:</strong> {selectedClient.username}</p>
                    <p><strong>Email:</strong> {selectedClient.email}</p>
                    <p><strong>Role:</strong> {selectedClient.role}</p>
                    <p><strong>Fidelity Level:</strong> {selectedClient.niveau_fidelete}</p>
                    <button onClick={() => setSelectedClient(null)}>Close</button>
                </div>
            )}
        </div>
    );
};

// Optional inline styles
const thStyle: React.CSSProperties = {
    border: "1px solid #ccc",
    padding: "8px",
    backgroundColor: "#f5f5f5",
    textAlign: "left",
};

const tdStyle: React.CSSProperties = {
    border: "1px solid #ccc",
    padding: "8px",
};

const trStyle: React.CSSProperties = {
    borderBottom: "1px solid #eee",
};

export default Clients;
