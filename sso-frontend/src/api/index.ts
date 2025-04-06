import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react"
import { SSOFormProps } from "../hook"
import { Token } from "./types"

const ssoURL = 'http://localhost:8086'

export const ssoApi = createApi({
    baseQuery: fetchBaseQuery({baseUrl: ssoURL}),
    endpoints: build => ({
        login: build.query<Token, SSOFormProps>({
            query: body => ({
                url: '/login',
                method: 'POST',
                body
            })
        })
    }),
})

export const {
    useLoginQuery,
    useLazyLoginQuery
} = ssoApi