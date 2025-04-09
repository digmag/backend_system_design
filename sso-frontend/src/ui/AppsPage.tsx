import { useApps } from "../hook"

export const AppsPage = () => {
    const {AuthowiredComponent} = useApps()
    return (
        <AuthowiredComponent />
    )
}