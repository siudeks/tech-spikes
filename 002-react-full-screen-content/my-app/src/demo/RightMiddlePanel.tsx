import { DetailsList, IDetailsColumnRenderTooltipProps, IDetailsHeaderProps, IRenderFunction, ScrollablePane, ScrollbarVisibility, Sticky, StickyPositionType, TooltipHost } from "@fluentui/react";

export const RightMiddlePanel: React.FC<{}> = props => {
    const onRenderDetailsHeader: IRenderFunction<IDetailsHeaderProps> = (props, defaultRender) => {
        if (!props) {
            return null;
        }
        const onRenderColumnHeaderTooltip: IRenderFunction<IDetailsColumnRenderTooltipProps> = tooltipHostProps => (
            <TooltipHost {...tooltipHostProps} />
        );
        return (
            <Sticky stickyPosition={StickyPositionType.Header} isScrollSynced>
                {defaultRender!({
                    ...props,
                    onRenderColumnHeaderTooltip,
                })}
            </Sticky>
        );
    };
    const columns = [
        {
            key: "name",
            name: "Name",
            isResizable: true,
            minWidth: 100,
            onRender: (item: string) => {
                return <span>{item}</span>;
            },
        },
    ];

    return (
        <div style={{ position: "relative", height: "100%" }}>
            <ScrollablePane scrollbarVisibility={ScrollbarVisibility.auto}>
                <DetailsList
                    onRenderDetailsHeader={onRenderDetailsHeader}
                    compact={true}
                    items={[...Array(200)].map((_, i) => `Item ${i + 1}`)}
                    columns={columns}
                ></DetailsList>
            </ScrollablePane>
        </div>

    );
}
