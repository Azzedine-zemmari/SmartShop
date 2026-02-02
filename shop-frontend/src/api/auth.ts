export interface LoginRequest {
    username: string;
    password: string;
}

export interface UserDto {
    id: number;
    username: string;
    role?: string;
}

export const login = async (
    data: LoginRequest
): Promise<UserDto> => {
    const response = await fetch("http://localhost:8080/api/v1/user/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        credentials: "include",
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        const error = await response.text();
        throw new Error(error);
    }

    return response.json();
};
