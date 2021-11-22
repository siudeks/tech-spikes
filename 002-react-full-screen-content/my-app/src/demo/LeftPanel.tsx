import { Stack } from "@fluentui/react";

export const LeftContent: React.FC<{}> = props => {
    return (
        <Stack verticalFill>
            <Stack.Item
                verticalFill
                styles={{
                    root: { width: "150px", background: "#DBADB1" }
                }}>
                <Stack>
                    <Stack.Item>Left Item 1</Stack.Item>
                    <Stack.Item>Left Item 2</Stack.Item>
                </Stack>
            </Stack.Item>
        </Stack>

    );
}
