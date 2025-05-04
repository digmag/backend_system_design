import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react"
import { SSOFormProps } from "../hook"
import { Apps, AppsCreate, Token } from "./types"
const ssoLocalhost = "localhost"
const ssoIp = "185.103.70.190"
const ssoURL = `http://${ssoLocalhost}:8080`

export const ssoApi = createApi({
    baseQuery: fetchBaseQuery({baseUrl: ssoURL}),
    endpoints: build => ({
        login: build.query<Token, SSOFormProps>({
            query: body => ({
                url: '/api/sso/login',
                method: 'POST',
                body
            })
        }),
        myApps: build.query<Array<Apps>, void>({
            query: () => ({
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`
                },
                url: '/api/sso/apps',
                method: "GET"
            })
        }),
        authowire: build.mutation<void, AppsCreate>({
            query: body => ({
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`
                },
                url: '/api/sso/authowire',
                method: 'POST',
                body
            })
        })
    }),
})
export const {
    useLoginQuery,
    useLazyLoginQuery,
    useMyAppsQuery,
    useAuthowireMutation
} = ssoApi