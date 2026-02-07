import { useEffect, useState } from "react";
import { getClients, getClientInfo, deleteClient, updateClient } from "../api/ClientApi";
import { logout } from "../api/auth";
import { useNavigate } from "react-router-dom";

const Clients = () => {
    const [clients, setClients] = useState<any[]>([]);
    const [selectedClient, setSelectedClient] = useState<any | null>(null);
    const [editingClient, setEditingClient] = useState<any | null>(null);
    const [formData, setFormData] = useState<any>({
        nom: "",
        username: "",
        email: "",
        role: "",
        niveau_fidelete: ""
    });

        const navigate = useNavigate();

    const fetchClients = async () => {
        try {
            const data = await getClients();
            setClients(data);
        } catch (error) {
            console.error(error);
        }
    };
    
    useEffect(() => {
        fetchClients();
    }, []);

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
            setClients((prev) => prev.filter((client) => client.id !== id));
            if (selectedClient?.id === id) setSelectedClient(null);
            alert("Client deleted successfully!");
        } catch (error) {
            console.error("Error deleting client:", error);
            alert("Failed to delete client.");
        }
    };

    const handleEdit = (client: any) => {
        setEditingClient(client);
        setFormData({
            nom: client.nom,
            username: client.username,
            email: client.email,
            role: client.role,
            niveau_fidelete: client.niveau_fidelete
        });
    };

    const handleUpdate = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const updatedClient = await updateClient(editingClient.id, formData);

            setClients((prev) =>
                prev.map((c) =>
                    c.id === updatedClient.id ? updatedClient : c
                )
            );

            setEditingClient(null);
            alert("Client updated successfully!");
        } catch (error) {
            console.error("Update failed:", error);
            alert("Failed to update client");
        }
    };

    const getFidelityBadgeColor = (level: string) => {
        switch(level) {
            case 'PLATINUM': return '#E5E4E2';
            case 'GOLD': return '#FFD700';
            case 'SILVER': return '#C0C0C0';
            default: return '#CD7F32';
        }
    };
        const handleLogout = async () =>{
        await logout();
        navigate("/login");
    }

    return (
        <div style={styles.container}>
            <div style={styles.header}>
                <h1 style={styles.title}>Client Management</h1>
                <p style={styles.subtitle}>{clients.length} total clients</p>
            </div>
            
                   <button
        onClick={handleLogout}
        className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700"
    >
        Logout
    </button>

            {clients.length === 0 ? (
                <div style={styles.emptyState}>
                    <svg style={styles.emptyIcon} fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
                    </svg>
                    <p style={styles.emptyText}>No clients found</p>
                </div>
            ) : (
                <div style={styles.tableContainer}>
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                <th style={styles.th}>ID</th>
                                <th style={styles.th}>Name</th>
                                <th style={styles.th}>Username</th>
                                <th style={styles.th}>Email</th>
                                <th style={styles.th}>Role</th>
                                <th style={styles.th}>Fidelity</th>
                                <th style={styles.th}>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {clients.map((client) => (
                                <tr key={client.id} style={styles.tr}>
                                    <td style={styles.td}>{client.id}</td>
                                    <td style={{...styles.td, fontWeight: 500}}>{client.nom}</td>
                                    <td style={styles.td}>{client.username}</td>
                                    <td style={styles.td}>{client.email}</td>
                                    <td style={styles.td}>
                                        <span style={{
                                            ...styles.roleBadge,
                                            backgroundColor: client.role === 'ADMIN' ? '#FEE2E2' : '#DBEAFE',
                                            color: client.role === 'ADMIN' ? '#991B1B' : '#1E40AF'
                                        }}>
                                            {client.role}
                                        </span>
                                    </td>
                                    <td style={styles.td}>
                                        <span style={{
                                            ...styles.fidelityBadge,
                                            backgroundColor: getFidelityBadgeColor(client.niveau_fidelete),
                                            color: client.niveau_fidelete === 'PLATINUM' ? '#374151' : '#1F2937'
                                        }}>
                                            {client.niveau_fidelete}
                                        </span>
                                    </td>
                                    <td style={styles.td}>
                                        <div style={styles.actionButtons}>
                                            <button onClick={() => handleShowInfo(client.id)} style={styles.viewButton}>
                                                View
                                            </button>
                                            <button onClick={() => handleEdit(client)} style={styles.editButton}>
                                                Edit
                                            </button>
                                            <button onClick={() => handleDelete(client.id)} style={styles.deleteButton}>
                                                Delete
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}

            {selectedClient && (
                <div style={styles.modal}>
                    <div style={styles.modalOverlay} onClick={() => setSelectedClient(null)} />
                    <div style={styles.modalContent}>
                        <div style={styles.modalHeader}>
                            <h2 style={styles.modalTitle}>Client Details</h2>
                            <button onClick={() => setSelectedClient(null)} style={styles.closeButton}>
                                ×
                            </button>
                        </div>
                        <div style={styles.modalBody}>
                            <div style={styles.infoRow}>
                                <span style={styles.infoLabel}>ID:</span>
                                <span style={styles.infoValue}>{selectedClient.id}</span>
                            </div>
                            <div style={styles.infoRow}>
                                <span style={styles.infoLabel}>Name:</span>
                                <span style={styles.infoValue}>{selectedClient.nom}</span>
                            </div>
                            <div style={styles.infoRow}>
                                <span style={styles.infoLabel}>Username:</span>
                                <span style={styles.infoValue}>{selectedClient.username}</span>
                            </div>
                            <div style={styles.infoRow}>
                                <span style={styles.infoLabel}>Email:</span>
                                <span style={styles.infoValue}>{selectedClient.email}</span>
                            </div>
                            <div style={styles.infoRow}>
                                <span style={styles.infoLabel}>Role:</span>
                                <span style={{
                                    ...styles.roleBadge,
                                    backgroundColor: selectedClient.role === 'ADMIN' ? '#FEE2E2' : '#DBEAFE',
                                    color: selectedClient.role === 'ADMIN' ? '#991B1B' : '#1E40AF'
                                }}>
                                    {selectedClient.role}
                                </span>
                            </div>
                            <div style={styles.infoRow}>
                                <span style={styles.infoLabel}>Fidelity Level:</span>
                                <span style={{
                                    ...styles.fidelityBadge,
                                    backgroundColor: getFidelityBadgeColor(selectedClient.niveau_fidelete),
                                    color: selectedClient.niveau_fidelete === 'PLATINUM' ? '#374151' : '#1F2937'
                                }}>
                                    {selectedClient.niveau_fidelete}
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            )}

            {editingClient && (
                <div style={styles.modal}>
                    <div style={styles.modalOverlay} onClick={() => setEditingClient(null)} />
                    <div style={styles.modalContent}>
                        <div style={styles.modalHeader}>
                            <h2 style={styles.modalTitle}>Edit Client</h2>
                            <button onClick={() => setEditingClient(null)} style={styles.closeButton}>
                                ×
                            </button>
                        </div>
                        <form onSubmit={handleUpdate} style={styles.form}>
                            <div style={styles.formGroup}>
                                <label style={styles.label}>Name</label>
                                <input
                                    style={styles.input}
                                    placeholder="Enter name"
                                    value={formData.nom}
                                    onChange={(e) => setFormData({ ...formData, nom: e.target.value })}
                                />
                            </div>

                            <div style={styles.formGroup}>
                                <label style={styles.label}>Username</label>
                                <input
                                    style={styles.input}
                                    placeholder="Enter username"
                                    value={formData.username}
                                    onChange={(e) => setFormData({ ...formData, username: e.target.value })}
                                />
                            </div>

                            <div style={styles.formGroup}>
                                <label style={styles.label}>Email</label>
                                <input
                                    style={styles.input}
                                    type="email"
                                    placeholder="Enter email"
                                    value={formData.email}
                                    onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                                />
                            </div>

                            <div style={styles.formGroup}>
                                <label style={styles.label}>Role</label>
                                <select
                                    style={styles.select}
                                    value={formData.role}
                                    onChange={(e) => setFormData({ ...formData, role: e.target.value })}
                                >
                                    <option value="">Select Role</option>
                                    <option value="CLIENT">CLIENT</option>
                                    <option value="ADMIN">ADMIN</option>
                                </select>
                            </div>

                            <div style={styles.formGroup}>
                                <label style={styles.label}>Fidelity Level</label>
                                <select
                                    style={styles.select}
                                    value={formData.niveau_fidelete}
                                    onChange={(e) => setFormData({ ...formData, niveau_fidelete: e.target.value })}
                                >
                                    <option value="">Select Level</option>
                                    <option value="BASIC">BASIC</option>
                                    <option value="SILVER">SILVER</option>
                                    <option value="GOLD">GOLD</option>
                                    <option value="PLATINUM">PLATINUM</option>
                                </select>
                            </div>

                            <div style={styles.formActions}>
                                <button type="button" onClick={() => setEditingClient(null)} style={styles.cancelButton}>
                                    Cancel
                                </button>
                                <button type="submit" style={styles.saveButton}>
                                    Save Changes
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

const styles: { [key: string]: React.CSSProperties } = {
    container: {
        maxWidth: "1200px",
        margin: "0 auto",
        padding: "40px 20px",
        fontFamily: "-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif"
    },
    header: {
        marginBottom: "32px"
    },
    title: {
        fontSize: "32px",
        fontWeight: "700",
        color: "#111827",
        margin: "0 0 8px 0"
    },
    subtitle: {
        fontSize: "16px",
        color: "#6B7280",
        margin: 0
    },
    tableContainer: {
        backgroundColor: "#FFFFFF",
        borderRadius: "12px",
        boxShadow: "0 1px 3px rgba(0, 0, 0, 0.1), 0 1px 2px rgba(0, 0, 0, 0.06)",
        overflow: "hidden"
    },
    table: {
        width: "100%",
        borderCollapse: "collapse"
    },
    th: {
        padding: "16px 24px",
        backgroundColor: "#F9FAFB",
        borderBottom: "1px solid #E5E7EB",
        textAlign: "left",
        fontSize: "12px",
        fontWeight: "600",
        color: "#6B7280",
        textTransform: "uppercase",
        letterSpacing: "0.05em"
    },
    tr: {
        borderBottom: "1px solid #F3F4F6",
        transition: "background-color 0.2s"
    },
    td: {
        padding: "16px 24px",
        fontSize: "14px",
        color: "#374151"
    },
    actionButtons: {
        display: "flex",
        gap: "8px"
    },
    viewButton: {
        padding: "6px 12px",
        fontSize: "13px",
        fontWeight: "500",
        color: "#1F2937",
        backgroundColor: "#F3F4F6",
        border: "none",
        borderRadius: "6px",
        cursor: "pointer",
        transition: "background-color 0.2s"
    },
    editButton: {
        padding: "6px 12px",
        fontSize: "13px",
        fontWeight: "500",
        color: "#1E40AF",
        backgroundColor: "#DBEAFE",
        border: "none",
        borderRadius: "6px",
        cursor: "pointer",
        transition: "background-color 0.2s"
    },
    deleteButton: {
        padding: "6px 12px",
        fontSize: "13px",
        fontWeight: "500",
        color: "#991B1B",
        backgroundColor: "#FEE2E2",
        border: "none",
        borderRadius: "6px",
        cursor: "pointer",
        transition: "background-color 0.2s"
    },
    roleBadge: {
        display: "inline-block",
        padding: "4px 12px",
        fontSize: "12px",
        fontWeight: "600",
        borderRadius: "12px",
        textTransform: "uppercase",
        letterSpacing: "0.025em"
    },
    fidelityBadge: {
        display: "inline-block",
        padding: "4px 12px",
        fontSize: "12px",
        fontWeight: "700",
        borderRadius: "12px",
        textTransform: "uppercase",
        letterSpacing: "0.025em"
    },
    emptyState: {
        textAlign: "center",
        padding: "80px 20px",
        backgroundColor: "#FFFFFF",
        borderRadius: "12px",
        boxShadow: "0 1px 3px rgba(0, 0, 0, 0.1)"
    },
    emptyIcon: {
        width: "64px",
        height: "64px",
        color: "#D1D5DB",
        margin: "0 auto 16px"
    },
    emptyText: {
        fontSize: "16px",
        color: "#6B7280",
        margin: 0
    },
    modal: {
        position: "fixed",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        zIndex: 1000
    },
    modalOverlay: {
        position: "absolute",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: "rgba(0, 0, 0, 0.5)"
    },
    modalContent: {
        position: "relative",
        backgroundColor: "#FFFFFF",
        borderRadius: "12px",
        boxShadow: "0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04)",
        maxWidth: "500px",
        width: "90%",
        maxHeight: "90vh",
        overflow: "auto"
    },
    modalHeader: {
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: "24px",
        borderBottom: "1px solid #E5E7EB"
    },
    modalTitle: {
        fontSize: "20px",
        fontWeight: "600",
        color: "#111827",
        margin: 0
    },
    closeButton: {
        fontSize: "32px",
        fontWeight: "300",
        color: "#6B7280",
        backgroundColor: "transparent",
        border: "none",
        cursor: "pointer",
        padding: "0",
        width: "32px",
        height: "32px",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        borderRadius: "6px",
        transition: "background-color 0.2s"
    },
    modalBody: {
        padding: "24px"
    },
    infoRow: {
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: "12px 0",
        borderBottom: "1px solid #F3F4F6"
    },
    infoLabel: {
        fontSize: "14px",
        fontWeight: "600",
        color: "#6B7280"
    },
    infoValue: {
        fontSize: "14px",
        color: "#111827"
    },
    form: {
        padding: "24px"
    },
    formGroup: {
        marginBottom: "20px"
    },
    label: {
        display: "block",
        fontSize: "14px",
        fontWeight: "500",
        color: "#374151",
        marginBottom: "6px"
    },
    input: {
        width: "100%",
        padding: "10px 12px",
        fontSize: "14px",
        color: "#111827",
        backgroundColor: "#FFFFFF",
        border: "1px solid #D1D5DB",
        borderRadius: "6px",
        outline: "none",
        transition: "border-color 0.2s, box-shadow 0.2s",
        boxSizing: "border-box"
    },
    select: {
        width: "100%",
        padding: "10px 12px",
        fontSize: "14px",
        color: "#111827",
        backgroundColor: "#FFFFFF",
        border: "1px solid #D1D5DB",
        borderRadius: "6px",
        outline: "none",
        transition: "border-color 0.2s, box-shadow 0.2s",
        boxSizing: "border-box",
        cursor: "pointer"
    },
    formActions: {
        display: "flex",
        justifyContent: "flex-end",
        gap: "12px",
        marginTop: "24px",
        paddingTop: "24px",
        borderTop: "1px solid #E5E7EB"
    },
    cancelButton: {
        padding: "10px 20px",
        fontSize: "14px",
        fontWeight: "500",
        color: "#374151",
        backgroundColor: "#FFFFFF",
        border: "1px solid #D1D5DB",
        borderRadius: "6px",
        cursor: "pointer",
        transition: "background-color 0.2s"
    },
    saveButton: {
        padding: "10px 20px",
        fontSize: "14px",
        fontWeight: "500",
        color: "#FFFFFF",
        backgroundColor: "#2563EB",
        border: "none",
        borderRadius: "6px",
        cursor: "pointer",
        transition: "background-color 0.2s"
    }
};

export default Clients;