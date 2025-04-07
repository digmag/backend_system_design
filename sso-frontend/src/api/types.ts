export type Token = {
    token: string
}
export type Apps = {
    id: string
    appId: string
}

export type AppsCreate = Pick<Apps, 'appId'>