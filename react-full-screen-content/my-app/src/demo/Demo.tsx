import { Stack } from "@fluentui/react";
import { LeftContent } from "./LeftPanel";
import { RightPanel } from "./RightPanel";

export const Demo: React.FC<{}> = props => {

    return (
        <div style={{ height: "100vh" }}>
            <Stack horizontal styles={{ root: { height: "100%" } }}>
                <Stack.Item>
                    <LeftContent />
                </Stack.Item>
                <Stack.Item styles={{ root: { width: "100%" } }}>
                    <RightPanel />
                </Stack.Item>
            </Stack>
        </div>);
}
